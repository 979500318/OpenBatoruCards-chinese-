package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R2_CodeAntiLeadSpray extends Card {

    public SIGNI_R2_CodeAntiLeadSpray()
    {
        setImageSets("WX25-P1-077");

        setOriginalName("コードアンチ　ナマリスプ");
        setAltNames("コードアンチナマリスプ Koodo Anchi Nemurisupu");
        setDescription("jp",
                "@U：あなたのメインフェイズの間、このカードがあなたの＜古代兵器＞のシグニの効果によってデッキからトラッシュに置かれたとき、対戦相手のパワー3000以下のシグニ１体を対象とし、あなたのエナゾーンから＜古代兵器＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、それをバニッシュする。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Code Anti Lead Spray");
        setDescription("en",
                "@U: During your main phase, when this card is put from your deck into the trash by the effect of your <<Ancient Weapon>> SIGNI, target 1 of your opponent's SIGNI with power 3000 or less, and you may put 1 <<Ancient Weapon>> SIGNI from your ener zone into the trash. If you do, banish it." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may pay %X. If you do, banish it."
        );

		setName("zh_simplified", "古兵代号 熔铅炉");
        setDescription("zh_simplified", 
                "@U :你的主要阶段期间，当这张牌因为你的<<古代兵器>>精灵的效果从牌组放置到废弃区时，对战对手的力量3000以下的精灵1只作为对象，可以从你的能量区把<<古代兵器>>精灵1张放置到废弃区。这样做的场合，将其破坏。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.ANCIENT_WEAPON).fromEner()).get();
                
                if(trash(cardIndex))
                {
                    banish(target);
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            if(target != null && payEner(Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
