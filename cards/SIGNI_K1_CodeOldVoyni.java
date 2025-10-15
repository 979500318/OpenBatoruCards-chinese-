package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_K1_CodeOldVoyni extends Card {

    public SIGNI_K1_CodeOldVoyni()
    {
        setImageSets("WXDi-P09-079");

        setOriginalName("コードオールド　ヴォイニ");
        setAltNames("コードオールドヴォイニ Koodo Oorudo Voini");
        setDescription("jp",
                "@U $T1：あなたのメインフェイズの間、あなたのデッキからレベル１のシグニ１枚がトラッシュに置かれたとき、そのシグニを場に出す。ターン終了時、そのシグニを場からトラッシュに置く。\n" +
                "@E #C：あなたのデッキの上からカードを３枚トラッシュに置く。"
        );

        setName("en", "Voyni, Code: Old");
        setDescription("en",
                "@U $T1: During your main phase, when a level one SIGNI is put into your trash from your deck, put that SIGNI onto your field. At end of turn, put that SIGNI on your field into their owner's trash.\n" +
                "@E #C: Put the top three cards of your deck into your trash."
        );
        
        setName("en_fan", "Code Old Voyni");
        setDescription("en_fan",
                "@U $T1: During your main phase, when a level 1 SIGNI is put from your deck into the trash, put that SIGNI onto the field. If you do, at the end of the turn, put that SIGNI from the field into the trash.\n" +
                "@E #C: Put the top 3 cards of your deck into the trash."
        );

		setName("zh_simplified", "古卒代号 伏尼契");
        setDescription("zh_simplified", 
                "@U $T1 :你的主要阶段期间，当从你的牌组把等级1的精灵1张放置到废弃区时，将那张精灵出场。回合结束时，那只精灵从场上放置到废弃区。\n" +
                "@E #C:从你的牌组上面把3张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            auto.setUseLimit(UseLimit.TURN, 1);
            auto.enableEventSourceSelection();

            registerEnterAbility(new CoinCost(1), this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN && caller.isEffectivelyAtLocation(CardLocation.DECK_MAIN) &&
                   CardType.isSIGNI(caller.getCardReference().getType()) && caller.getIndexedInstance().matchesLevelByRef(getAbility(), 1,1) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(putOnField(caller))
            {
                int instanceId = caller.getIndexedInstance().getInstanceId();
                callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                    if(caller.isSIGNIOnField() && caller.getIndexedInstance().getInstanceId() == instanceId)
                    {
                        trash(caller);
                    }
                });
            }
        }

        private void onEnterEff()
        {
            millDeck(3);
        }
    }
}
