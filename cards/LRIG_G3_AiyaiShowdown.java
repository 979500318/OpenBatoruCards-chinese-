package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventMove;

public final class LRIG_G3_AiyaiShowdown extends Card {

    public LRIG_G3_AiyaiShowdown()
    {
        setImageSets("WXDi-P11-007", "WXDi-P11-007U");

        setOriginalName("アイヤイ★ショーダウン");
        setAltNames("アイヤイショーダウン Aiyai Shoodaun");
        setDescription("jp",
                "@U $T1：あなたのターンの間、あなたのエナゾーンからシグニ１枚が手札に加わるか場に出たとき、【エナチャージ１】をする。\n" +
                "@E：あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。\n" +
                "@A $G1 %G0：あなたのシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがバトルによってシグニ１体をバニッシュしたとき、あなたのエナゾーンからレベル２以下のシグニを１枚まで対象とし、それとこのシグニの場所を入れ替える。それの@E能力は発動しない。@@を得る。"
        );

        setName("en", "Aiyai ★ Showdown");
        setDescription("en",
                "@U $T1: During your turn, when a SIGNI from your Ener Zone is added to your hand or enters your field, [[Ener Charge 1]].\n" +
                "@E: Add up to one target SIGNI from your Ener Zone to your hand.\n" +
                "@A $G1 %G0: Target SIGNI on your field gains@>@U: When this SIGNI vanishes a SIGNI through battle, swap the position of up to one target level two or less SIGNI from your Ener Zone with this SIGNI. The @E abilities of SIGNI put onto your field this way do not activate.@@until end of turn."
        );
        
        setName("en_fan", "Aiyai★Showdown");
        setDescription("en_fan",
                "@U $T1: During your turn, when a SIGNI from your ener zone is added to your hand or put onto the field, [[Ener Charge 1]].\n" +
                "@E: Target up to 1 SIGNI from your ener zone, and add it to your hand.\n" +
                "@A $G1 %G0: Target 1 of your SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI banishes a SIGNI in battle, target up to 1 level 2 or lower SIGNI from your ener zone, and exchange its position with this SIGNI. Its @E abilities don't activate."
        );

		setName("zh_simplified", "艾娅伊★梭哈");
        setDescription("zh_simplified", 
                "@U $T1 :你的回合期间，当从你的能量区把精灵1张加入手牌或出场时，[[能量填充1]]。\n" +
                "@E :从你的能量区把精灵1张最多作为对象，将其加入手牌。\n" +
                "@A $G1 %G0:你的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U 当这只精灵因为战斗把精灵1只破坏时，从你的能量区把等级2以下的精灵1张最多作为对象，将其与这只精灵的场所交换。其的@E能力不能发动。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AIYAI);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            registerEnterAbility(this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && caller.isEffectivelyAtLocation(CardLocation.ENER) &&
                   (EventMove.getDataMoveLocation() == CardLocation.HAND || CardLocation.isSIGNI(EventMove.getDataMoveLocation())) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            enerCharge(1);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.BANISH, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() == null && getEvent().getSourceCardIndex() == getAbility().getSourceCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex sourceCardIndex = getAbility().getSourceCardIndex();
            
            CardIndex target = sourceCardIndex.getIndexedInstance().playerTargetCard(0,1, new TargetFilter(TargetHint.MOVE).own().SIGNI().withLevel(0,2).fromEner().playableAs(sourceCardIndex)).get();
            if(target != null)
            {
                sourceCardIndex.getIndexedInstance().putInEner(sourceCardIndex);
                sourceCardIndex.getIndexedInstance().putOnField(target, sourceCardIndex.getPreTransientLocation(), Enter.DONT_ACTIVATE);
            }
        }
    }
}
