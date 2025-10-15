package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityCopyModifier;
import open.batoru.data.ability.modifiers.CardNameModifier;

public final class LRIG_G4_AnneFourthGrowthOfTraditionsTone extends Card {

    public LRIG_G4_AnneFourthGrowthOfTraditionsTone()
    {
        setImageSets("WX24-P4-022", "WX24-P4-022U");

        setOriginalName("正調の成長　アン＝フォース");
        setAltNames("セイチョウノセイチョウアンフォース Seichou no Seichou An Foosu");
        setDescription("jp",
                "@C：このルリグはあなたのルリグトラッシュにあるレベル３の＜アン＞と同じカード名としても扱い、そのルリグの@U能力を得る。\n" +
                "@E @[エクシード４]@：あなたのデッキの上からカードを４枚見る。その中からシグニを３枚まで場に出し、残りをエナゾーンに置く。\n" +
                "@A $G1 @[@|アイディール|@]@ %G0：&E４枚以上@0対戦相手のシグニゾーンかエナゾーンから、白のカードを１枚まで対象とする。赤、青、緑、黒についても同様に行う。それらを手札に戻す。"
        );

        setName("en", "Anne-Fourth, Growth of Tradition's Tone");
        setDescription("en",
                "@C: This LRIG is also treated as having the same card name as a level 3 <<Anne>> in your LRIG trash, and gains that LRIG's @U abilities.\n" +
                "@E @[Exceed 4]@: Look at the top 4 cards of your deck. Put up to 3 SIGNI from among them onto the field, and put the rest into the ener zone.\n" +
                "@A $G1 @[@|Ideal|@]@ %G0: &E4 or more@0 Target up to 1 white card from your opponent's SIGNI zone or ener zone. Do the same for red, blue, green, and black. Return them to their hand."
        );

		setName("zh_simplified", "正调的成长 安=FORTH");
        setDescription("zh_simplified", 
                "@C 这只分身也视作与你的分身废弃区的等级3的<<アン>>相同牌名，得到那张分身的@U能力。\n" +
                "@E @[超越 4]@:从你的牌组上面看4张牌。从中把精灵3张最多出场，剩下的放置到能量区。\n" +
                "@A $G1 :理想%G0&E4张以上@0从对战对手的精灵区或能量区把，白色的牌1张最多作为对象。红色、蓝色、绿色、黑色也同样进行。将这些返回手牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ANN);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(4);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }


    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final CardColor[] COLORS = {CardColor.WHITE, CardColor.RED, CardColor.BLUE, CardColor.GREEN, CardColor.BLACK};
        public IndexedInstance(int cardId)
        {
            super(cardId);

            TargetFilter filter = new TargetFilter().own().LRIG().withLRIGType(CardLRIGType.ANN).withLevel(3).fromTrash(DeckType.LRIG);
            registerConstantAbility(new CardNameModifier(filter), new AbilityCopyModifier(filter, ability -> ability instanceof AutoAbility));

            registerEnterAbility(new ExceedCost(4), this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Ideal");
            act.setRecollect(4);
        }

        private void onEnterEff()
        {
            look(4);

            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromLooked().playable());
            putOnField(data);
            
            putInEner(getCardsInLooked(getOwner()));
        }

        private void onActionEff()
        {
            if(getAbility().isRecollectFulfilled())
            {
                DataTable<CardIndex> data = new DataTable<>();
                for(CardColor color : COLORS)
                {
                    CardIndex target = playerTargetCard(0,1,
                        new TargetFilter(TargetHint.HAND).OP().fromLocation(CardLocation.ENER, CardLocation.SIGNI_LEFT,CardLocation.SIGNI_CENTER,CardLocation.SIGNI_RIGHT).
                        anyCard().withColor(color)
                    ).get();
                    if(!data.contains(target)) data.add(target);
                }
                addToHand(data);
            }
        }
    }
}
