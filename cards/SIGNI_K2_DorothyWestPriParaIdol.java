package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K2_DorothyWestPriParaIdol extends Card {

    public SIGNI_K2_DorothyWestPriParaIdol()
    {
        setImageSets("WXDi-P10-074");

        setOriginalName("プリパラアイドル　ドロシー・ウェスト");
        setAltNames("プリパラアイドルドロシーウェスト Puripara Aidoru Doroshii Uesuto");
        setDescription("jp",
                "@C：あなたのトラッシュに＜プリパラ＞のシグニが５枚以上あるかぎり、このシグニのパワーは＋4000される。\n" +
                "@E：あなたのデッキの上からカードを３枚トラッシュに置く。" +
                "~#：対戦相手のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );

        setName("en", "Dorothy West, Pripara Idol");
        setDescription("en",
                "@C: As long as there are five or more <<Pripara>> SIGNI in your trash, this SIGNI gets +4000 power.\n" +
                "@E: Put the top three cards of your deck into your trash." +
                "~#You may discard a card. If you do, target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Dorothy West, PriPara Idol");
        setDescription("en_fan",
                "@C: As long as there are 5 or more <<PriPara>> SIGNI in your trash, this SIGNI gets +4000 power.\n" +
                "@E: Put the top 3 cards of your deck into the trash." +
                "~#Target 1 of your opponent's SIGNI, and you may discard 1 card from your hand. If you do, until end of turn, it gets --12000 power."
        );

		setName("zh_simplified", "美妙天堂偶像 桃乐丝·威斯特");
        setDescription("zh_simplified", 
                "@C :你的废弃区的<<プリパラ>>精灵在5张以上时，这只精灵的力量+4000。\n" +
                "@E :从你的牌组上面把3张牌放置到废弃区。" +
                "~#对战对手的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PRIPARA);
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

            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
            
            registerEnterAbility(this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.PRIPARA).fromTrash().getValidTargetsCount() >= 5 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            millDeck(3);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
