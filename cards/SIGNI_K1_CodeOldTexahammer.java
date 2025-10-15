package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K1_CodeOldTexahammer extends Card {

    public SIGNI_K1_CodeOldTexahammer()
    {
        setImageSets("WX25-P1-099");

        setOriginalName("コードオールド　テキサハンマ");
        setAltNames("コードオールドテキサハンマ Koodo Oorudo Tekisahanma");
        setDescription("jp",
                "@U：あなたのメインフェイズの間、このカードがあなたの＜古代兵器＞のシグニの効果によってデッキからトラッシュに置かれたとき、このカードをトラッシュから場に出してもよい。\n" +
                "~#：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Code Old Texahammer");
        setDescription("en",
                "@U: During your main phase, when this card is put from your deck into the trash by the effect of your <<Ancient Weapon>> SIGNI, you may put this card from your trash onto the field." +
                "~#Target 1 SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "古卒代号 德州铁锤");
        setDescription("zh_simplified", 
                "@U :你的主要阶段期间，当这张牌因为你的<<古代兵器>>精灵的效果从牌组放置到废弃区时，可以把这张牌从废弃区出场。" +
                "~#从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            auto.setActiveLocation(CardLocation.DECK_MAIN);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN &&
                   getEvent().getSourceAbility() != null && getEvent().getSourceCost() == null && isOwnCard(getEvent().getSource()) &&
                   CardType.isSIGNI(getEvent().getSource().getCardReference().getType()) && getEvent().getSource().getSIGNIClass().matches(CardSIGNIClass.ANCIENT_WEAPON) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(getCardIndex().getLocation() == CardLocation.TRASH && isPlayable() && playerChoiceActivate())
            {
                putOnField(getCardIndex());
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}
