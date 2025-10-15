package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
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

public final class LRIG_R4_YuzukiFourDanceOfPassionateFlames extends Card {

    public LRIG_R4_YuzukiFourDanceOfPassionateFlames()
    {
        setImageSets("WX24-P4-015", "WX24-P4-015U");

        setOriginalName("熾炎舞　遊月・肆");
        setAltNames("シエンブユヅキヨン Shienbu Yuzuki Yon");
        setDescription("jp",
                "@C：このルリグはあなたのルリグトラッシュにあるレベル３の＜ユヅキ＞と同じカード名としても扱い、そのルリグの@U能力を得る。\n" +
                "@E @[エクシード４]@：あなたのライフクロス１枚をクラッシュしてもよい。その後、対戦相手のシグニをこの方法でクラッシュしたライフクロスの枚数に１を加えた数対象とし、それらをバニッシュする。\n" +
                "@A $G1 @[@|真直ぐな心|@]@ %R0：&E４枚以上@0対戦相手のセンタールリグがレベル３以上の場合、対戦相手は自分のエナゾーンからカード５枚を選びトラッシュに置く。"
        );

        setName("en", "Yuzuki Four, Dance of Passionate Flames");
        setDescription("en",
                "@C: This LRIG is also treated as having the same card name as a level 3 <<Yuzuki>> in your LRIG trash, and gains that LRIG's @U abilities.\n" +
                "@E @[Exceed 4]@: You may crush 1 of your life cloth. Then, target a number of your opponent's SIGNI equal to the number of life cloth crushed this way plus 1, and banish them.\n" +
                "@A $G1 @[@|Straightforward Heart|@]@ %R0: &E4 or more@0 If your opponent's center LRIG is level 3 or higher, your opponent chooses 5 cards from their ener zone, and puts them into the trash."
        );

		setName("zh_simplified", "炽炎舞 游月·肆");
        setDescription("zh_simplified", 
                "@C 这只分身也视作与你的分身废弃区的等级3的<<ユヅキ>>相同牌名，得到那张分身的@U能力。\n" +
                "@E @[超越 4]@:可以把你的生命护甲1张击溃。然后，对战对手的精灵这个方法击溃的生命护甲的张数加1的数量作为对象，将这些破坏。\n" +
                "@A $G1 :直率的心%R0&E4张以上@0对战对手的核心分身在等级3以上的场合，对战对手从自己的能量区选5张牌放置到废弃区。（4张以下的场合，选这些全部）\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUZUKI);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(4);
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

            TargetFilter filter = new TargetFilter().own().LRIG().withLRIGType(CardLRIGType.YUZUKI).withLevel(3).fromTrash(DeckType.LRIG);
            registerConstantAbility(new CardNameModifier(filter), new AbilityCopyModifier(filter, ability -> ability instanceof AutoAbility));

            registerEnterAbility(new ExceedCost(4), this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Straightforward Heart");
            act.setRecollect(4);
        }

        private void onEnterEff()
        {
            int count = (getLifeClothCount(getOwner()) > 0 && playerChoiceActivate() && crush(getOwner()) != null) ? 1 : 0;
            
            DataTable<CardIndex> targets = playerTargetCard(count+1, new TargetFilter(TargetHint.BANISH).OP().SIGNI());
            banish(targets);
        }

        private void onActionEff()
        {
            if(getAbility().isRecollectFulfilled() && getLRIG(getOpponent()).getIndexedInstance().getLevel().getValue() >= 3)
            {
                DataTable<CardIndex> data = playerTargetCard(getOpponent(), Math.min(getEnerCount(getOpponent()), 5), new TargetFilter(TargetHint.BURN).own().fromEner());
                trash(data);
            }
        }
    }
}
