package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class LRIG_G3_BangAdvancingToBattle extends Card {
    
    public LRIG_G3_BangAdvancingToBattle()
    {
        setImageSets("WXDi-P02-023", "SPDi07-09","SPDi08-09");
        
        setOriginalName("タタカイススム　バン");
        setAltNames("タタカイススムバン Tatakai Susumu Ban");
        setDescription("jp",
                "=T ＜うちゅうのはじまり＞\n" +
                "^E：対戦相手は自分のシグニ１体を選びエナゾーンに置く。\n" +
                "@U：あなたのアタックフェイズ開始時、このルリグの下にカードが５枚以上ある場合、[[エナチャージ１]]をする。７枚以上ある場合、あなたのレベル３の緑のシグニ１体を対象とし、ターン終了時まで、それは[[ランサー]]を得る。\n" +
                "@A $G1 %G0：あなたの他のルリグの下にあるすべてのカードをこのルリグの下に置く。"
        );
        
        setName("en", "Bang, Read to Fight");
        setDescription("en",
                "=T <<UCHU NO HAJIMARI>>\n" +
                "^E: Your opponent chooses a SIGNI on their field and puts it into its owner's Ener Zone.\n" +
                "@U: At the beginning of your attack phase, if there are five or more cards underneath this LRIG, [[Ener Charge 1]]. If there are seven or more cards underneath it, target green level three SIGNI on your field gains [[Lancer]] until end of turn.\n" +
                "@A $G1 %G0: Put all cards underneath all other LRIG on your field under this LRIG."
        );
        
        setName("en_fan", "Bang, Advancing to Battle");
        setDescription("en_fan",
                "=T <<Universe's Beginning>>\n" +
                "^E: Your opponent chooses 1 of their SIGNI and puts it into their ener zone.\n" +
                "@U: At the beginning of your attack phase, if there are 5 or more cards under this LRIG, [[Ener Charge 1]]. If there are 7 or more cards under this LRIG, target 1 of your level 3 green SIGNI, and until end of turn, that SIGNI gains [[Lancer]].\n" +
                "@A $G1 %G0: Put all of the cards under your other LRIGs under this LRIG."
        );
        
		setName("zh_simplified", "战斗冲 梆");
        setDescription("zh_simplified", 
                "=T<<うちゅうのはじまり>>\n" +
                "^E:对战对手选自己的精灵1只放置到能量区。\n" +
                "@U :你的攻击阶段开始时，这只分身的下面的牌在5张以上的场合，[[能量填充1]]。7张以上的场合，你的等级3的绿色的精灵1只作为对象，直到回合结束时为止，其得到[[枪兵]]。\n" +
                "@A $G1 %G0:你的其他的分身的下面的全部的牌放置到这只分身的下面。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.BANG);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            EnterAbility enter = registerEnterAbility(this::onEnterEff);
            enter.setCondition(this::onEnterEffCond);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onEnterEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.ENER).own().SIGNI()).get();
            putInEner(cardIndex);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            int countUnder = new TargetFilter().own().under(getLRIG(getOwner())).getValidTargetsCount();
            
            if(countUnder >= 5) enerCharge(1);
            
            if(countUnder >= 7)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.GREEN).withLevel(3)).get();
                if(target != null) attachAbility(target, new StockAbilityLancer(), ChronoDuration.turnEnd());
            }
        }
        
        private void onActionEff()
        {
            forEachLRIGOnField(getOwner(), cardIndex -> {
                if(cardIndex == getCardIndex()) return;
                
                forEachCardUnder(cardIndex, cardIndexUnder -> {
                    attach(getCardIndex(), cardIndexUnder, CardUnderType.UNDER_GENERIC);
                });
            });
        }
    }
}
