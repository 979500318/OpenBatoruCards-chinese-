package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K1_AssistMitoTsukinoLevel1Mallet extends Card {

    public LRIGA_K1_AssistMitoTsukinoLevel1Mallet()
    {
        setImageSets("WXDi-CP01-019");

        setOriginalName("【アシスト】月ノ美兎　レベル１【杵】");
        setAltNames("アシストツキノミトレベルイチオノ Ashisuto Tsukino Mito Reberu Ichi Ne Assist Mito Assist Tsukino");
        setDescription("jp",
                "@E：対戦相手のレベル１のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E：あなたのトラッシュから＜バーチャル＞のシグニ１枚を対象とし、それを場に出す。"
        );

        setName("en", "[Assist] Mito, Level 1 [Mallet]");
        setDescription("en",
                "@E: Vanish target level one SIGNI on your opponent's field.\n@E: Put target <<Virtual>> SIGNI from your trash onto your field."
        );
        
        setName("en_fan", "[Assist] Mito Tsukino Level 1 [Mallet]");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 1 SIGNI, and banish it.\n" +
                "@E: Target 1 <<Virtual>> SIGNI from your trash, and put it onto the field."
        );

		setName("zh_simplified", "【支援】月之美兔 等级1【杵】");
        setDescription("zh_simplified", 
                "@E :对战对手的等级1的精灵1只作为对象，将其破坏。\n" +
                "@E :从你的废弃区把<<バーチャル>>精灵1张作为对象，将其出场。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MITO);
        setColor(CardColor.BLACK);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(1)).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash().playable()).get();
            putOnField(target);
        }
    }
}
