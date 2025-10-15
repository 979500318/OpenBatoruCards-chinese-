package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
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
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_G3_SangaVOGUE3EX extends Card {
    
    public LRIG_G3_SangaVOGUE3EX()
    {
        setImageSets("WXDi-P06-010", "WXDi-P06-010U");
        
        setOriginalName("VOGUE3-EX サンガ");
        setAltNames("ボーグスリーイーエックスサンガ Boogu Surii Ii Ekkusu Sanga");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるシグニが持つ色が合計３種類以上ある場合、以下の２つから１つを選ぶ。\n" +
                "$$1あなたのエナゾーンからカード１枚を対象とし、それを手札に加える。\n" + 
                "$$2【エナチャージ１】\n" +
                "@E：【エナチャージ２】\n" +
                "@A $G1 %G0：対戦相手のパワー12000以上のシグニ１体を対象とし、それをバニッシュする。あなたのエナゾーンからカード１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Sanga, Vogue 3 - EX");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have three or more different colors among SIGNI on your field, choose one of the following.\n" +
                "$$1 Add target card from your Ener Zone to your hand.\n" +
                "$$2 [[Ener Charge 1]].\n" +
                "@E: [[Ener Charge 2]]\n" +
                "@A $G1 %G0: Vanish target SIGNI on your opponent's field with power 12000 or more. Add target card from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Sanga, VOGUE 3-EX");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 3 or more colors among SIGNI on your field, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 card from your ener zone, and add it to your hand.\n" +
                "$$2 [[Ener Charge 1]]\n" +
                "@E: [[Ener Charge 2]].\n" +
                "@A $G1 %G0: Target 1 of your opponent's SIGNI with power 12000 or more, and banish it. Target 1 card from your ener zone, and add it to your hand."
        );
        
		setName("zh_simplified", "VOGUE3-EX 山河");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的精灵持有颜色在合计3种类以上的场合，从以下的2种选1种。\n" +
                "$$1 从你的能量区把1张牌作为对象，将其加入手牌。\n" +
                "$$2 [[能量填充1]]\n" +
                "@E :[[能量填充2]]\n" +
                "@A $G1 %G0:对战对手的力量12000以上的精灵1只作为对象，将其破坏。从你的能量区把1张牌作为对象，将其加入手牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SANGA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
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
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(CardAbilities.getColorsCount(getSIGNIOnField(getOwner())) >= 3)
            {
                if(playerChoiceMode() == 1)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().fromEner()).get();
                    addToHand(target);
                } else {
                    enerCharge(1);
                }
            }
        }
        
        private void onEnterEff()
        {
            enerCharge(2);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(12000,0)).get();
            banish(target);
            
            target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().fromEner()).get();
            addToHand(target);
        }
    }
}
