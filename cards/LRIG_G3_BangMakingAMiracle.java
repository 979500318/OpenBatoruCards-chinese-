package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class LRIG_G3_BangMakingAMiracle extends Card {
    
    public LRIG_G3_BangMakingAMiracle()
    {
        setImageSets("WXDi-P06-009", "WXDi-P06-009U");
        
        setOriginalName("キセキオコス　バン");
        setAltNames("キセキオコスバン Kiseki Okosu Ban");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このルリグの下からカード１枚をルリグトラッシュに置いてもよい。そうした場合、[[エナチャージ１]]をする。\n" +
                "@E：このターン、あなたの中央のシグニゾーンにあるシグニは[[ランサー]]を得る。\n" +
                "@A $G1 %G %G：あなたの緑のシグニ１体を対象とし、ターン終了時まで、それは[[Ｓランサー]]を得る。"
        );
        
        setName("en", "Bang, Making a Miracle");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may put a card underneath this LRIG into its owner's LRIG trash. If you do, [[Ener Charge 1]].\n" +
                "@E: SIGNI in your center SIGNI Zone gain [[Lancer]] this turn.\n" +
                "@A $G1 %G %G: Target green SIGNI on your field gains [[S Lancer]] until end of turn.  "
        );
        
        setName("en_fan", "Bang, Making a Miracle");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, you may put 1 card from under this LRIG into the LRIG trash. If you do, [[Ener Charge 1]].\n" +
                "@E: This turn, the SIGNI in your center SIGNI zone gains [[Lancer]].\n" +
                "@A $G1 %G %G: Target 1 of your green SIGNI, and until end of turn, it gains [[S Lancer]]."
        );
        
		setName("zh_simplified", "创造奇迹 梆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以从这只分身的下面把1张牌放置到分身废弃区。这样做的场合，[[能量填充1]]。\n" +
                "@E :这个回合，你的中央的精灵区的精灵得到[[枪兵]]。\n" +
                "@A $G1 %G %G:你的绿色的精灵1只作为对象，直到回合结束时为止，其得到[[S枪兵]]。（当持有[[S枪兵]]的精灵战斗把精灵破坏时，对战对手有生命护甲的场合，将其1张击溃。没有的场合，给予对战对手伤害）\n"
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 2)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().under(getCardIndex())).get();
            
            if(trash(target))
            {
                enerCharge(1);
            }
        }
        
        private void onEnterEff()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().SIGNI().fromLocation(CardLocation.SIGNI_CENTER),
                new AbilityGainModifier(this::onAttachedConstEffModGetSample)
            );
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityLancer());
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.GREEN)).get();
            if(target != null) attachAbility(target, new StockAbilitySLancer(), ChronoDuration.turnEnd());
        }
    }
}
