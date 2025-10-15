package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class LRIG_X3_EternalQ extends Card {

    public LRIG_X3_EternalQ()
    {
        setImageSets("WXDi-P11-010A", Mask.DOUBLE_FACED_UR+"WXDi-P11-010A");
        setDoubleFacedCardPairImageSetHint("WXDi-P11-010B");

        setOriginalName("夢限　－Ｑ－");
        setAltNames("ムゲンキュウ Mugen Kyuu Mugen Q");
        setDescription("jp",
                "@U：あなたのグロウフェイズ開始時、このゲームの間、あなたの場にいる《夢限　－Ｑ－》のリミットを＋１する。その後、このルリグのリミットが９以上の場合、あなたの手札とエナゾーンとトラッシュにあるすべてのカードをデッキに加えてシャッフルし、このルリグ以外の、あなたのルリグデッキと場にあるすべてのカードをゲームから除外し、このルリグを裏向きにする。\n" +
                "@E：あなたのトラッシュから無色のシグニ１枚と無色ではないシグニ１枚を対象とし、それらを手札に加える。"
        );

        setName("en", "Mugen -Q-");
        setDescription("en",
                "@U: At the beginning of your grow phase, increase the limit of \"Mugen -Q-\" on your field by one for the duration of the game. Then, if the limit of this LRIG is nine or more, shuffle all cards in your hand, Ener Zone, and trash into your deck, remove all cards in your LRIG Deck and on your field other than this LRIG from the game, and turn this LRIG face down.\n" +
                "@E: Add target colorless SIGNI and target non-colorless SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Eternal -Q-");
        setDescription("en_fan",
                "@U: At the beginning of your grow phase, during this game, your \"Eternal -Q-\" gets +1 limit. Then, if this LRIG's limit is 9 or more, shuffle all cards in your hand, ener zone, and trash into your deck, exclude all cards in your LRIG deck and on your field other than your LRIG from the game, and flip this LRIG.\n" +
                "@E: Target 1 colorless and 1 non-colorless SIGNI from your trash, and add them to your hand."
        );

		setName("zh_simplified", "梦限 -Q-");
        setDescription("zh_simplified", 
                "@U :你的成长阶段开始时，这场游戏期间，你的场上的《夢限　-Q-》的界限+1。然后，这只分身的界限在9以上的场合，你的手牌和能量区和废弃区的全部的牌加入牌组洗切，这只分身以外的，你的分身牌组和场上的全部的牌从游戏除外，这只分身变为里向。\n" +
                "@E :从你的废弃区把无色的精灵1张和不是无色的精灵1张作为对象，将这些加入手牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MUGEN);
        setCost(Cost.colorless(2));
        setLevel(3);
        setLimit(5);

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

            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.GROW ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainValue(getCardIndex(), getLimit(),1d, ChronoDuration.permanent());

            if(getLimit().getValue() >= 9)
            {
                exclude(getSIGNIOnField(getOwner()));
                exclude(new TargetFilter().own().fromSafeLocation(CardLocation.SIGNI_LEFT,CardLocation.SIGNI_CENTER,CardLocation.SIGNI_RIGHT).
                    custom(cardIndex -> (cardIndex.getIndexedInstance() == null || cardIndex.getIndexedInstance().isState(CardStateFlag.KILL_ME)) && !cardIndex.wasMovedByOverride()).getExportedData()
                );
                returnToDeck(getCardsInHand(getOwner()), DeckPosition.TOP);
                returnToDeck(getCardsInEner(getOwner()).andRemoveIf(CardIndex::wasMovedByOverride), DeckPosition.TOP);
                returnToDeck(getCardsInTrash(getOwner()).andRemoveIf(CardIndex::wasMovedByOverride), DeckPosition.TOP);
                shuffleDeck();
                
                exclude(getCardsInLRIGDeck(getOwner()));
                
                exclude(new TargetFilter().own().fromLocation(CardLocation.LRIG_ASSIST_LEFT).getExportedData());
                exclude(new TargetFilter().own().fromLocation(CardLocation.LRIG_ASSIST_RIGHT).getExportedData());
                exclude(new TargetFilter().own().under(getLRIG(getOwner())).getExportedData());
                
                transform(getCardIndex(), getLinkedImageSets().get(0), ChronoDuration.permanent());
            }
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = new DataTable<>();

            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withColor()).fromTrash()).get();
            if(target != null) data.add(target);

            target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor().fromTrash()).get();
            if(target != null) data.add(target);

            addToHand(data);
        }
    }
}
