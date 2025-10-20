package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_W2_Code2434PomuRainpuff extends Card {

    public SIGNI_W2_Code2434PomuRainpuff()
    {
        setImageSets("WXDi-CP01-040");

        setOriginalName("コード２４３４　ぽむ れいんぱふ");
        setAltNames("コードニジサンジポムレインパフ Koodo Nijisanji Pomu Reinpafu");
        setDescription("jp",
                "@U：あなたのルリグ１体がアタックしたとき、アップ状態のこのシグニをダウンしてもよい。そうした場合、あなたのデッキの一番上を公開する。そのカードが＜バーチャル＞のシグニの場合、カードを１枚引く。"
        );

        setName("en", "Pomu Rainpuff, Code 2434");
        setDescription("en",
                "@U: Whenever a LRIG on your field attacks, you may down this upped SIGNI. If you do, reveal the top card of your deck. If that card is a <<Virtual>> SIGNI, draw a card."
        );
        
        setName("en_fan", "Code 2434 Pomu Rainpuff");
        setDescription("en_fan",
                "@U: Whenever your LRIG attacks, you may down this SIGNI. If you do, reveal the top card of your deck. If it is a <<Virtual>> SIGNI, draw 1 card."
        );

		setName("zh_simplified", "2434代号 Pomu Rainpuff");
        setDescription("zh_simplified", 
                "@U :当你的分身1只攻击时，可以把竖直状态的这只精灵横置。这样做的场合，你的牌组最上面公开。那张牌是<<バーチャル>>精灵的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isLRIG(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if((getCardStateFlags().getValue() & CardStateFlag.DOWNED) == 0 && playerChoiceActivate() && down())
            {
                CardIndex cardIndex = reveal();
                
                if(cardIndex == null ||
                   !CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) || !cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.VIRTUAL) ||
                   draw(1).get() == null)
                {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
    }
}
