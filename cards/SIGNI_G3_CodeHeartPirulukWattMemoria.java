package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_G3_CodeHeartPirulukWattMemoria extends Card {

    public SIGNI_G3_CodeHeartPirulukWattMemoria()
    {
        setImageSets("WXDi-P10-042", "WXDi-P10-042P");

        setOriginalName("コードハート　ピルルクＷ//メモリア");
        setAltNames("コードハートピルルクＷメモリア Koodo Haato Piruruku Watto Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのデッキの一番上を公開する。そのカードがスペルの場合、カードを１枚引く。そうでない場合、【エナチャージ１】をする。\n" +
                "@A $T1 %X @[手札からスペルを１枚捨てる]@：あなたのシグニ１体を対象とし、ターン終了時まで、それは@>@C $T1 #D：このシグニよりパワーの低い対戦相手のシグニ１体を対象とし、それをバニッシュする。@@を得る。"
        );

        setName("en", "Piruluk W//Memoria, Code: Heart");
        setDescription("en",
                "@U: At the beginning of your attack phase, reveal the top card of your deck. If that card is a spell, draw a card. If it isn't, [[Ener Charge 1]].\n" +
                "@A $T1 %X @[Discard a spell]@: Target SIGNI on your field gains@>@A $T1 #D: Vanish target SIGNI on your opponent's field with power less than the power of this SIGNI.@@until end of turn."
        );
        
        setName("en_fan", "Code Heart Piruluk Watt//Memoria");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, reveal the top card of your deck. If it is a spell, draw 1 card. If it is not, [[Ener Charge 1]].\n" +
                "@A $T1 %X @[Discard 1 spell from your hand]@: Target 1 of your SIGNI, and until end of turn, it gains:" +
                "@>@A $T1 #D: Target 1 of your opponent's SIGNI with power less than this SIGNI's, and banish it."
        );

		setName("zh_simplified", "爱心代号 皮璐璐可W//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的牌组最上面公开。那张牌是魔法的场合，抽1张牌。不是的场合，[[能量填充1]]。\n" +
                "@A $T1 %X从手牌把魔法1张舍弃:你的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@A $T1 #D:比这只精灵的力量低的对战对手的精灵1只作为对象，将其破坏。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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

            ActionAbility act = registerActionAbility(new AbilityCostList(new EnerCost(Cost.colorless(1)), new DiscardCost(new TargetFilter().spell())), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                if(cardIndex.getIndexedInstance().getTypeByRef() == CardType.SPELL)
                {
                    draw(1);
                } else {
                    enerCharge(1);
                }
            }
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            
            if(target != null)
            {
                ActionAbility attachedAct = new ActionAbility(new DownCost(), this::onAttachedActEff);
                attachedAct.setUseLimit(UseLimit.TURN, 1);
                
                attachAbility(target, attachedAct, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedActEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,getAbility().getSourceCardIndex().getIndexedInstance().getPower().getValue()-1)).get();
            getAbility().getSourceCardIndex().getIndexedInstance().banish(target);
        }
    }
}
