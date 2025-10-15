package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityCopyModifier;
import open.batoru.data.ability.modifiers.CardNameModifier;

public final class LRIG_K4_AlfouBrideOfBlackAffection extends Card {

    public LRIG_K4_AlfouBrideOfBlackAffection()
    {
        setImageSets("WX24-P4-025", "WX24-P4-025U");

        setOriginalName("黒愛の花嫁　アルフォウ");
        setAltNames("コクアイノハナヨメアルフォウ Kokuai no Hanayome Arufou");
        setDescription("jp",
                "@C：このルリグはあなたのルリグトラッシュにあるレベル３の＜アルフォウ＞と同じカード名としても扱い、そのルリグの@U能力を得る。\n" +
                "@E @[エクシード４]@：あなたか対戦相手のデッキの上からカードを６枚トラッシュに置く。\n" +
                "@A $G1 @[@|クレイヴ|@]@ %K0：&E４枚以上@0あなたのトラッシュから##を持たないすべてのカードをデッキに加えてシャッフルする。ターン終了時まで、対戦相手のすべてのシグニのパワーをこの方法でデッキに加えたカード１枚につき－1000する。"
        );

        setName("en", "Alfou, Bride of Black Affection");
        setDescription("en",
                "@C: This LRIG is also treated as having the same card name as a level 3 <<Alfou>> in your LRIG trash, and gains that LRIG's @U abilities.\n" +
                "@E @[Exceed 4]@: Put the top 6 cards of your or your opponent's deck into the trash.\n" +
                "@A $G1 @[@|Jealousy|@]@ %K0: &E4 or more@0 Shuffle all cards without ## @[Life Burst]@ from your trash into the deck. Until end of turn, all of your opponent's SIGNI get --1000 power for each shuffled this way card."
        );

		setName("zh_simplified", "黑爱的花嫁 阿尔芙");
        setDescription("zh_simplified", 
                "@C 这只分身也视作与你的分身废弃区的等级3的<<アルフォウ>>相同牌名，得到那张分身的@U能力。\n" +
                "@E @[超越 4]@:从你或对战对手的牌组上面把6张牌放置到废弃区。\n" +
                "@A $G1 :忌妒%K0&E4张以上@0从你的废弃区把不持有##的全部的牌加入牌组洗切。直到回合结束时为止，对战对手的全部的精灵的力量依据这个方法加入牌组的牌的数量，每有1张就-1000。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ALFOU);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
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

            TargetFilter filter = new TargetFilter().own().LRIG().withLRIGType(CardLRIGType.ALFOU).withLevel(3).fromTrash(DeckType.LRIG);
            registerConstantAbility(new CardNameModifier(filter), new AbilityCopyModifier(filter, ability -> ability instanceof AutoAbility));

            registerEnterAbility(new ExceedCost(4), this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Jealousy");
            act.setRecollect(4);
        }

        private void onEnterEff()
        {
            millDeck(playerChoiceAction(ActionHint.OWN, ActionHint.OPPONENT) == 1 ? getOwner() : getOpponent(), 6);
        }

        private void onActionEff()
        {
            if(getAbility().isRecollectFulfilled())
            {
                DataTable<CardIndex> data = getCardsInTrash(getOwner()).andRemoveIf(cardIndex -> cardIndex.getIndexedInstance().findLifeBurstAbility().isPresent());
                int countReturned = returnToDeck(data, DeckPosition.TOP);
                shuffleDeck();

                forEachSIGNIOnField(getOpponent(), cardIndex -> gainPower(cardIndex, -1000 * countReturned, ChronoDuration.turnEnd()));
            }
        }
    }
}
