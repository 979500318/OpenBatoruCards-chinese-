package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventMove;

public final class LRIG_K3_YaminoNeedlePrincessOfCreationThree extends Card {

    public LRIG_K3_YaminoNeedlePrincessOfCreationThree()
    {
        setImageSets("WX25-P1-034", "WX25-P1-034U");
        setLinkedImageSets("WX25-P1-TK1","WX25-P1-TK2","WX25-P1-TK3","WX25-P1-TK4","WX25-P1-TK5","WX25-P1-TK6");

        setOriginalName("創造の針姫　ヤミノ＝Ⅲ");
        setAltNames("ツムギシトビラアトノルスリー Souzou no Harihime Yamino Three");
        setDescription("jp",
                "@U $TP $T1：あなたの＜怪異＞のシグニ１体が場を離れたとき、クラフトの《幻怪　ヤミノザンシ》１つを場に出す。\n" +
                "@A $G1 @[@|ビギニング|@]@ %K0：ヤミノアーツのクラフトから２種類を１枚ずつ公開しルリグデッキに加える。"
        );

        setName("en", "Yamino-III, Needle Princess of Creation");
        setDescription("en",
                "@U $TP $T1: When 1 of your <<Apparition>> SIGNI leaves the field, put 1 \"Yaminozanshi, Phantom Apparition\" craft onto the field.\n" +
                "@A $G1 @[@|Beginning|@]@ %K0: Reveal 2 different Yamino ARTS crafts one by one, and add them to your LRIG deck."
        );

		setName("zh_simplified", "创造的针姬 暗乃 = III 式");
        setDescription("zh_simplified", 
                "@U $TP $T1 当你的<<怪異>>精灵1只离场时，衍生的《幻怪:ヤミノザンシ》1只出场。\n" +
                "@A $G1 开端%K0:从暗乃必杀的衍生把2种类各1张公开加入分身牌组。（暗乃必杀有5种类）\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YAMINO);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Beginning");
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnTurn() && isOwnCard(caller) && caller.isSIGNIOnField() &&
                   caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.APPARITION) &&
                   !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = craft("WX25-P1-TK6");
            
            if(!putOnField(cardIndex))
            {
                exclude(cardIndex);
            }
        }
        
        private void onActionEff()
        {
            DataTable<String> data = playerChoiceCatalog(2, filter -> {
                filter.filterSets("WX25-P1-TK");
                filter.filterType(CardType.ARTS);
            });
            for(int i=0;i<data.size();i++) returnToDeck(craft(data.get(i)), DeckPosition.TOP);
        }
    }
}

