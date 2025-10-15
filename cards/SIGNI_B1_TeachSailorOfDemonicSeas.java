package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_TeachSailorOfDemonicSeas extends Card {

    public SIGNI_B1_TeachSailorOfDemonicSeas()
    {
        setImageSets("WXK01-055");

        setOriginalName("魔海の船員　ティーチ");
        setAltNames("マカイノセンインティーチ Makai no Senin Tiichi");
        setDescription("jp",
                "@U：このカードが手札からトラッシュに置かれたとき、あなたの手札が２枚以下であなたのメインフェイズの場合、このシグニをトラッシュから場に出してもよい。" +
                "~#：カードを１枚引く。"
        );

        setName("en", "Teach, Sailor of Demonic Seas");
        setDescription("en",
                "@U: During your main phase, when this card is put from your hand into the trash, if there are 2 or less cards in your hand, you may put this SIGNI from your trash onto the field." +
                "~#Draw 1 card."
        );

		setName("zh_simplified", "魔海的船员 蒂奇");
        setDescription("zh_simplified", 
                "@U :当这张牌从手牌放置到废弃区时，你的手牌在2张以下且是你的主要阶段的场合，可以把这张精灵从废弃区出场。" +
                "~#抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY);
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
            auto.setActiveLocation(CardLocation.HAND);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(getCardIndex().getLocation() == CardLocation.TRASH && getHandCount(getOwner()) <= 2 && playerChoiceActivate())
            {
                putOnField(getCardIndex());
            }
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
        }
    }
}
