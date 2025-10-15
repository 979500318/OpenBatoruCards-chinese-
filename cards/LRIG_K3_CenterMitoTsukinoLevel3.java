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
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CollaboLiverCost;
import open.batoru.data.ability.cost.EnerCost;

import java.util.List;

public final class LRIG_K3_CenterMitoTsukinoLevel3 extends Card {

    public LRIG_K3_CenterMitoTsukinoLevel3()
    {
        setImageSets("WXDi-CP01-008");

        setOriginalName("【センター】月ノ美兎　レベル３");
        setAltNames("センターツキノミトレベルサン Sentaa Tsukino Mito Reberu San Center Mito Center Tsuki");
        setDescription("jp",
                "@E：コラボライバー２人を呼ぶ。\n" +
                "@A @[コラボライバー１人とコラボする]@：あなたのトラッシュから黒のシグニ１枚を対象とし、それを場に出す。\n" +
                "@A $G1 @[@|＃みとの生放送|@]@ %K0：各プレイヤーは自分のデッキの上からカードを１０枚トラッシュに置く。その後、あなたは自分のトラッシュからそれぞれ名前の異なるカード３枚を対象とし、それらをデッキに加えてシャッフルする。"
        );

        setName("en", "[Center] Mito, Level 3");
        setDescription("en",
                "@E: Invite two Collab Livers.\n@A @[Collaborate with a Collab Liver]@: Put target black SIGNI from your trash onto your field.\n@A $G1 #Mito'sLiveBroadcasting %K0: Each player puts the top ten cards of their deck into their trash. Then, shuffle three target cards with different names from your trash into your deck."
        );
        
        setName("en_fan", "[Center] Mito Tsukino Level 3");
        setDescription("en_fan",
                "@E: Invite 2 CollaboLivers.\n" +
                "@A @[Collab with 1 CollaboLiver]@: Target 1 black SIGNI from your trash, and put it onto the field.\n" +
                "@A $G1 @[@|#Mito's Livestream|@]@ %K0: Each player puts the top 10 cards of their deck into the trash. Then, you target 3 cards with different names from your trash, and shuffle them into your deck."
        );

		setName("zh_simplified", "【核心】月之美兔 等级3");
        setDescription("zh_simplified", 
                "@E :呼唤联动主播2人。\n" +
                "@A 与联动主播1人联动:从你的废弃区把黑色的精灵1张作为对象，将其出场。\n" +
                "@A $G1 #美兔的生放送%K0:各玩家从自己的牌组上面把10张牌放置到废弃区。然后，你从自己的废弃区把名字不同的3张牌作为对象，将这些加入牌组洗切。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MITO);
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

            registerEnterAbility(this::onEnterEff);

            registerActionAbility(new CollaboLiverCost(1), this::onActionEff1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.setName("Mito's Livestream");
        }

        private void onEnterEff()
        {
            inviteCollaboLivers(2);
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.BLACK).fromTrash().playable()).get();
            putOnField(target);
        }

        private void onActionEff2()
        {
            millDeck(10);
            millDeck(getOpponent(), 10);
            
            if(getCardsInTrash(getOwner()).stream().map(c -> c.getCardReference().getOriginalName()).distinct().count() >= 3)
            {
                DataTable<CardIndex> data = playerTargetCard(3, new TargetFilter(TargetHint.SHUFFLE).fromTrash(), this::onActionEff2TargetCond);
                if(data.size() == 3)
                {
                    returnToDeck(data, DeckPosition.TOP);
                    shuffleDeck();
                }
            }
        }
        private boolean onActionEff2TargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.isEmpty() || listPickedCards.stream().map(c -> c.getCardReference().getOriginalName()).distinct().count() == 3;
        }
    }
}
