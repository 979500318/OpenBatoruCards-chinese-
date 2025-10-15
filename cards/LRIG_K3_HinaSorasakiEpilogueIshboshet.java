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
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

import java.util.List;

public final class LRIG_K3_HinaSorasakiEpilogueIshboshet extends Card {

    public LRIG_K3_HinaSorasakiEpilogueIshboshet()
    {
        setImageSets("WXDi-CP02-010");

        setOriginalName("空崎ヒナ[終幕イシュ・ボシェテ]");
        setAltNames("ソラサキヒナシュウマクイシュボシェテ Sorasaki Hina Shuumaku Ishuboshete");
        setDescription("jp",
                "@U $T1：あなたのターンの間、コストか効果１つによっていずれかのプレイヤーのデッキからカードが合計３枚以上トラッシュに置かれたとき、ターン終了時まで、対戦相手のすべてのシグニのパワーを－3000する。\n" +
                "@A $G1 %K0：あなたのトラッシュからそれぞれレベルの異なるシグニ２枚を対象とし、それらを場に出す。" +
                "~{{A $T1 %X %X：あなたのトラッシュからシグニ１枚を対象とし、それを場に出す。"
        );

        setName("en", "Sorasaki Hina [Denouement: Ishbosheth]");
        setDescription("en",
                "@U $T1: During your turn, when a player puts a total of three or more cards from their deck into their trash by a cost or an effect, all SIGNI on your opponent's field get --3000 power until end of turn.\n@A $G1 %K0: Put two target SIGNI with different levels from your trash onto your field.~{{A $T1 %X %X: Put target SIGNI from your trash onto your field."
        );
        
        setName("en_fan", "Hina Sorasaki [Epilogue: Ishbóshet]");
        setDescription("en_fan",
                "@U $T1: During your turn, when a total of 3 or more cards are put from either player's deck into the trash by a cost or a single effect, until end of turn, all of your opponent's SIGNI get --3000 power.\n" +
                "@A $G1 %K0: Target 2 SIGNI with different levels from your trash, and put them onto the field." +
                "~{{A $T1 %X %X: Target 1 SIGNI from your trash, and put it onto the field."
        );

		setName("zh_simplified", "空崎日奈[终幕：伊施·波设]");
        setDescription("zh_simplified", 
                "@U $T1 :你的回合期间，当因为费用或效果1个从任一方的玩家的牌组把牌合计3张以上放置到废弃区时，直到回合结束时为止，对战对手的全部的精灵的力量-3000。\n" +
                "@A $G1 %K0:从你的废弃区把等级不同的精灵2张作为对象，将这些出场。\n" +
                "~{{A$T1 %X %X:从你的废弃区把精灵1张作为对象，将其出场。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HINA);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
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

            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            auto.getFlags().addValue(AbilityFlag.ACTIVE_ONCE_PER_EFFECT);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.GAME, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.colorless(2)), this::onActionEff2);
            act2.setUseLimit(UseLimit.TURN, 1);
            act2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && getEvent().getSourceAbility() != null && getEvent().isAtOnce(3) &&
                   caller.isEffectivelyAtLocation(CardLocation.DECK_MAIN) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(getSIGNIOnField(getOpponent()), -3000, ChronoDuration.turnEnd());
        }

        private void onActionEff1()
        {
            TargetFilter filter = new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable();
            if(filter.getExportedData().stream().mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).distinct().limit(2).count() >= 2 ||
               filter.getExportedData().stream().mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getDataByReferenceValues().size()).filter(size -> size > 0).limit(2).count() >= 2)
            {
                DataTable<CardIndex> data = playerTargetCard(2, filter, this::onActionEff1TargetCond);
                putOnField(data);
            }
        }
        private boolean onActionEff1TargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 2 &&
                    ((listPickedCards.get(0).getIndexedInstance().getLevel().getValue() != listPickedCards.get(1).getIndexedInstance().getLevel().getValue()) ||
                     (!listPickedCards.get(0).getIndexedInstance().getLevel().getDataByReferenceValues().isEmpty() && !listPickedCards.get(1).getIndexedInstance().getLevel().getDataByReferenceValues().isEmpty()));
        }

        private void onActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable()).get();
            putOnField(target);
        }
    }
}

