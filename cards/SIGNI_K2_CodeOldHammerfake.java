package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K2_CodeOldHammerfake extends Card {

    public SIGNI_K2_CodeOldHammerfake()
    {
        setImageSets("WX25-P1-104");

        setOriginalName("コードオールド　ハンマフェイク");
        setAltNames("コードオールドハンマフェイク Koodo Oorudo Hanmafeiku");
        setDescription("jp",
                "@U：あなたのメインフェイズの間、このカードがあなたの＜古代兵器＞のシグニの効果によってデッキからトラッシュに置かれたとき、このカードをトラッシュから場に出してもよい。" +
                "~#：あなたのトラッシュから#Gを持たないレベル２以下のシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Code Old Hammerfake");
        setDescription("en",
                "@U: During your main phase, when this card is put from your deck into the trash by the effect of your <<Ancient Weapon>> SIGNI, you may put this card from your trash onto the field." +
                "~#Target 1 level 2 or lower SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "古卒代号 伪造铁锤");
        setDescription("zh_simplified", 
                "@U :你的主要阶段期间，当这张牌因为你的<<古代兵器>>精灵的效果从牌组放置到废弃区时，可以把这张牌从废弃区出场。" +
                "~#从你的废弃区把不持有#G的等级2以下的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(2);
        setPower(8000);

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
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().withLevel(0,2).not(new TargetFilter().guard()).fromTrash()).get();
            
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}
