package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_W3_AzusaShirasuIntulitMortem extends Card {

    public LRIG_W3_AzusaShirasuIntulitMortem()
    {
        setImageSets("WXDi-CP02-007");

        setOriginalName("白洲アズサ[intulit mortem]");
        setAltNames("シラスアズサイントューリットモルテム Shirasu Azusa Intuuritto Morutemu");
        setDescription("jp",
                "@A $T1 %W %X：対戦相手のシグニ１体を対象とし、それを手札に戻す。あなたのライフクロスが３枚以上ある場合、代わりにそれをトラッシュに置く。\n" +
                "@A $G1 %W0：あなたのデッキの上からカードを７枚見る。その中からカードを２枚まで手札に加え、残りをシャッフルしてデッキの一番下に置く。" +
                "~{{A $T1 %X：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Shirasu Azusa [intulit mortem]");
        setDescription("en",
                "@A $T1 %W %X: Return target SIGNI on your opponent's field to its owner's hand. If you have three or more cards in your Life Cloth, instead put it into its owner's trash.\n@A $G1 %W0: Look at the top seven cards of your deck. Add up to two cards from among them to your hand and put the rest on the bottom of your deck in a random order.~{{A $T1 %X: Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Azusa Shirasu [intulit mortem]");
        setDescription("en_fan",
                "@A $T1 %W %X: Target 1 of your opponent's SIGNI, and return it to their hand. If you have 3 or more life cloth, put it into the trash instead.\n" +
                "@A $G1 %W0: Look at the top 7 cards of your deck. Add up to 2 cards from among them to your hand, and shuffle the rest and put them on the bottom of your deck." +
                "~{{A $T1 %X: Look at the top 3 cards of your deck. Reveal 1 card from among them, add it to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "白洲梓[intulit mortem]");
        setDescription("zh_simplified", 
                "@A $T1 %W%X:对战对手的精灵1只作为对象，将其返回手牌。你的生命护甲在3张以上的场合，作为替代，将其放置到废弃区。\n" +
                "@A $G1 %W0:从你的牌组上面看7张牌。从中把牌2张最多加入手牌，剩下的洗切放置到牌组最下面。\n" +
                "&nbsp;~{{A$T1 %X:从你的牌组上面看3张牌。从中把精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AZUSA);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
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

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);

            ActionAbility act3 = registerActionAbility(new EnerCost(Cost.colorless(1)), this::onActionEff3);
            act3.setUseLimit(UseLimit.TURN, 1);
            act3.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private void onActionEff1()
        {
            boolean matchLC = getLifeClothCount(getOwner()) >= 3;
            
            CardIndex target = playerTargetCard(new TargetFilter(matchLC ? TargetHint.TRASH : TargetHint.HAND).OP().SIGNI()).get();
            if(matchLC) trash(target);
            else addToHand(target);
        }
        
        private void onActionEff2()
        {
            look(7);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromLooked());
            addToHand(data);
            
            int countReturned = returnToDeck(getCardsInLooked(getOwner()), DeckPosition.BOTTOM);
            shuffleDeck(countReturned, DeckPosition.BOTTOM);
        }
        
        private void onActionEff3()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
