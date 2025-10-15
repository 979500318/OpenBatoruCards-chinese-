package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_AlfouRoseDoll extends Card {

    public LRIGA_K2_AlfouRoseDoll()
    {
        setImageSets("WX24-P4-041");

        setOriginalName("アルフォウローズドール");
        setAltNames("アルフォウローズドール Arufou Roozu Dooru");
        setDescription("jp",
                "@E：あなたのトラッシュからシグニ１枚を対象とし、それを場に出す。それの@E能力は発動しない。\n" +
                "@E %X：各プレイヤーは自分のデッキの上からカードを４枚トラッシュに置く。"
        );

        setName("en", "Alfou Rose Doll");
        setDescription("en",
                "@E: Target 1 SIGNI from your trash, and put it onto the field. Its @E abilities don't activate.\n" +
                "@E %X: Each player puts the top 4 cards of their deck into the trash."
        );

		setName("zh_simplified", "阿尔芙玫瑰娃娃");
        setDescription("zh_simplified", 
                "@E 从你的废弃区把精灵1张作为对象，将其出场。其的@E能力不能发动。\n" +
                "@E %X:各玩家从自己的牌组上面把4张牌放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ALFOU);
        setColor(CardColor.BLACK);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable()).get();
            putOnField(target, Enter.DONT_ACTIVATE);
        }
        
        private void onEnterEff2()
        {
            millDeck(4);
            millDeck(getOpponent(), 4);
        }
    }
}
