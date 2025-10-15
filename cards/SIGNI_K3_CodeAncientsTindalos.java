package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K3_CodeAncientsTindalos extends Card {

    public SIGNI_K3_CodeAncientsTindalos()
    {
        setImageSets("WX25-P2-061");

        setOriginalName("コードアンシエンツ　ティンダロス");
        setAltNames("コードアンシエンツティンダロス Koodo Anshientsu Tindarosu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のデッキの上からカードを４枚トラッシュに置く。このターンにこのシグニがトラッシュから場に出ていた場合、代わりに対戦相手のデッキの上からカードを６枚トラッシュに置く。\n\n" +
                "@U：あなたのメインフェイズの間、このカードがあなたの効果によってデッキからトラッシュに置かれたとき、%Kを支払ってもよい。そうした場合、このカードをトラッシュから場に出す。" +
                "~#：対戦相手のレベル２以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Code Ancients Tindalos");
        setDescription("en",
                "@U: At the beginning of your attack phase, put the top 4 cards of your opponent's deck into the trash. If this card entered the field from your trash this turn, put the top 6 cards of your opponent's deck into the trash instead.\n\n" +
                "@U: During your main phase, when this card is put from your deck into the trash by your effect, you may pay %K. If you do, put this card from your trash onto the field." +
                "~#Target 1 of your opponent's level 2 or lower SIGNI, and banish it."
        );

		setName("zh_simplified", "古神代号 廷达罗斯之猎犬");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从对战对手的牌组上面把4张牌放置到废弃区。这个回合这只精灵从废弃区出场的场合，作为替代，从对战对手的牌组上面把6张牌放置到废弃区。\n" +
                "@U :你的主要阶段期间，当这张牌因为你的效果从牌组放置到废弃区时，可以支付%K。这样做的场合，这张牌从废弃区出场。" +
                "~#对战对手的等级2以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.TRASH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setActiveLocation(CardLocation.DECK_MAIN);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            boolean enteredFromTrash = GameLog.getTurnRecordsCount(e -> e.getId() == GameEventId.ENTER && e.getCaller().getInstanceId() == getInstanceId() && e.getCaller().getOldLocation() == CardLocation.TRASH) > 0;
            millDeck(getOpponent(), !enteredFromTrash ? 4 : 6);
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return getCurrentPhase() == GamePhase.MAIN && getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSource()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2()
        {
            if(getCardIndex().getLocation() == CardLocation.TRASH && payEner(Cost.color(CardColor.BLACK, 1)))
            {
                putOnField(getCardIndex());
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
            banish(target);
        }
    }
}
