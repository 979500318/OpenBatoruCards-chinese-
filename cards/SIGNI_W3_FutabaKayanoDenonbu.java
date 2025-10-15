package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W3_FutabaKayanoDenonbu extends Card {

    public SIGNI_W3_FutabaKayanoDenonbu()
    {
        setImageSets("WXDi-P14-077", "WXDi-P14-077P");

        setOriginalName("電音部　茅野ふたば");
        setAltNames("デンオンブカヤノフタバ Denonbu Kayano Futaba");
        setDescription("jp",
                "@C：対戦相手のターンの間、あなたの他の＜電音部＞のシグニのパワーを＋2000する。\n" +
                "@U：あなたのアタックフェイズ開始時、このシグニのパワーが15000以上の場合、あなたのデッキの上からカードを３枚見る。その中から＜電音部＞のシグニを１枚まで公開し手札に加え、＜電音部＞のシグニを１枚までエナゾーンに置き、残りを好きな順番でデッキの一番下に置く。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "DEN-ON-BU Futaba Kayano");
        setDescription("en",
                "@C: During your opponent's turn, other <<DEN-ON-BU>> SIGNI on your field get +2000 power.\n@U: At the beginning of your attack phase, if this SIGNI's power is 15000 or more, look at the top three cards of your deck. Reveal up to one <<DEN-ON-BU>> SIGNI from among them and add it to your hand, put up to one <<DEN-ON-BU>> SIGNI into your Ener Zone, and put the rest on the bottom of your deck in any order." +
                "~#Put target upped SIGNI on your opponent's field into its owner's trash."
        );
        
        setName("en_fan", "Futaba Kayano, Denonbu");
        setDescription("en_fan",
                "@C: During your opponent's turn, your other <<Denonbu>> SIGNI get +2000 power.\n" +
                "@U: At the beginning of your attack phase, if this SIGNI's power is 15000 or more, look at the top 3 cards of your deck. Reveal up to 1 <<Denonbu>> SIGNI from among them, and add it to your hand, put up to 1 <<Denonbu>> SIGNI from among them into the ener zone, and put the rest to the bottom of your deck in any order." +
                "~#Target 1 of your opponent's upped SIGNI, and put it into the trash."
        );

		setName("zh_simplified", "电音部 茅野双叶 ");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，你的其他的<<電音部>>精灵的力量+2000。\n" +
                "@U :你的攻击阶段开始时，这只精灵的力量在15000以上的场合，从你的牌组上面看3张牌。从中把<<電音部>>精灵1张最多公开加入手牌，<<電音部>>精灵1张最多放置到能量区，剩下的任意顺序放置到牌组最下面。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DENONBU);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DENONBU).except(cardId), new PowerModifier(2000));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getPower().getValue() >= 15000)
            {
                look(3);
                
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.DENONBU).fromLooked()).get();
                reveal(cardIndex);
                addToHand(cardIndex);
                
                cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ENER).own().SIGNI().withClass(CardSIGNIClass.DENONBU).fromLooked()).get();
                putInEner(cardIndex);
                
                while(getLookedCount() > 0)
                {
                    cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().upped()).get();
            trash(target);
        }
    }
}
