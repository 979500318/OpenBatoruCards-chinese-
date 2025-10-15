package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G4_UnicarnPhantomApparitionPrincess extends Card {

    public SIGNI_G4_UnicarnPhantomApparitionPrincess()
    {
        setImageSets("WXK01-043");

        setOriginalName("幻怪姫　ユニカーン");
        setAltNames("ゲンカイキユニカーン Genkaiki Yunikaan");
        setDescription("jp",
                "@U $T1：あなたが緑のアーツを使用したとき、【エナチャージ１】をする。\n" +
                "@U：このシグニが対戦相手のシグニ１体をバニッシュしたとき、あなたのルリグトラッシュから緑のアーツ１枚を対象とし、%G %Gを支払ってもよい。そうした場合、それをルリグデッキに加える。" +
                "~#：カードを１枚引く。あなたのライフクロスが４枚以下の場合、追加で【エナチャージ１】をする。"
        );

        setName("en", "Unicarn, Phantom Apparition Princess");
        setDescription("en",
                "@U $T1: When you use green ARTS, [[Ener Charge 1]].\n" +
                "@U: Whenever this SIGNI banishes 1 of your opponent's SIGNI, target 1 green ARTS from your LRIG trash, and you may pay %G %G. If you do, add it to your LRIG deck." +
                "~#Draw 1 card. If you have 4 or less life cloth, additionally [[Ener Charge 1]]."
        );

		setName("zh_simplified", "幻怪姬 独角兽");
        setDescription("zh_simplified", 
                "@U $T1 :当你把绿色的必杀使用时，[[能量填充1]]。\n" +
                "@U :当这只精灵把对战对手的精灵1只破坏时，从你的分身废弃区把绿色的必杀1张作为对象，可以支付%G %G。这样做的场合，将其加入分身牌组。" +
                "~#抽1张牌。你的生命护甲在4张以下的场合，追加[[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(4);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.USE_ARTS, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().getColor().matches(CardColor.GREEN) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            enerCharge(1);
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TOP).own().ARTS().withColor(CardColor.GREEN).fromTrash(DeckType.LRIG)).get();
            
            if(target != null && payEner(Cost.color(CardColor.GREEN, 2)))
            {
                returnToDeck(target, DeckPosition.TOP);
            }
        }

        private void onLifeBurstEff()
        {
            draw(1);

            if(getLifeClothCount(getOwner()) <= 4)
            {
                enerCharge(1);
            }
        }
    }
}
