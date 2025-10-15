package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W3_Code2434YuikaShiina extends Card {

    public SIGNI_W3_Code2434YuikaShiina()
    {
        setImageSets("WXDi-CP01-025");

        setOriginalName("コード２４３４　椎名唯華");
        setAltNames("コードニジサンジシイナユイカ Koodo Nijisanji Shiina Yuika");
        setDescription("jp",
                "@C：このシグニがダウン状態であるかぎり、あなたの＜バーチャル＞のシグニのパワーを＋3000する。\n" +
                "@E：あなたのデッキの一番上を見る。そのカードをデッキの一番下に置いてもよい。\n" +
                "@A #D：あなたのデッキの一番上を公開する。そのカードが＜バーチャル＞のシグニの場合、カードを１枚引く。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Shiina Yuika, Code 2434");
        setDescription("en",
                "@C: As long as this SIGNI is downed, <<Virtual>> SIGNI on your field get +3000 power.\n@E: Look at the top card of your deck. You may put that card on the bottom of your deck.\n@A #D: Reveal the top card of your deck. If that card is a <<Virtual>> SIGNI, draw a card." +
                "~#Put target upped SIGNI on your opponent's field into its owner's trash."
        );
        
        setName("en_fan", "Code 2434 Yuika Shiina");
        setDescription("en_fan",
                "@C: As long as this SIGNI is downed, all of your <<Virtual>> SIGNI get +3000 power.\n" +
                "@E: Look at the top card of your deck. You may put that card on the bottom of your deck.\n" +
                "@A #D: Reveal the top card of your deck. If it is a <<Virtual>> SIGNI, draw 1 card." +
                "~#Target 1 of your opponent's upped SIGNI, and put it into the trash."
        );

		setName("zh_simplified", "2434代号 椎名唯华");
        setDescription("zh_simplified", 
                "@C 这只精灵在#D状态时，你的<<バーチャル>>精灵的力量+3000。\n" +
                "@E :看你的牌组最上面。可以把那张牌放置到牌组最下面。\n" +
                "@A #D:你的牌组最上面公开。那张牌是<<バーチャル>>精灵的场合，抽1张牌。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
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

            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL), new PowerModifier(3000));
            
            registerEnterAbility(this::onEnterEff);
            
            registerActionAbility(new DownCost(), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return (getCardStateFlags().getValue() & CardStateFlag.DOWNED) != 0 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = look();
            returnToDeck(cardIndex, cardIndex != null && playerChoiceActivate() ? DeckPosition.BOTTOM : DeckPosition.TOP);
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = reveal();
            if(cardIndex == null || !CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) || !cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.VIRTUAL) ||
               draw(1).get() == null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().upped()).get();
            trash(target);
        }
    }
}
