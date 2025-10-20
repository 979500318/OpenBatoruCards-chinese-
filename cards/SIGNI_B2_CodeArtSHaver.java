package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B2_CodeArtSHaver extends Card {

    public SIGNI_B2_CodeArtSHaver()
    {
        setImageSets("WX24-P1-069");

        setOriginalName("コードアート　Sエーバー");
        setAltNames("コードアートエスエーバー Koodo Aato Esu Eebaa");
        setDescription("jp",
                "@U:あなたがスペルを使用したとき、アップ状態のこのシグニをダウンしてもよい。そうした場合、カードを１枚引く。" +
                "~#：対戦相手のルリグ１体を対象とし、それをダウンする。"
        );

        setName("en", "Code Art S Haver");
        setDescription("en",
                "@U: Whenever you use a spell, you may down this upped SIGNI. If you do, draw 1 card." +
                "~#Target 1 of your opponent's LRIG, and down it."
        );

		setName("zh_simplified", "必杀代号 电动剃须刀");
        setDescription("zh_simplified", 
                "@U :当你把魔法使用时，可以把竖直状态的这只精灵横置。这样做的场合，抽1张牌。" +
                "~#对战对手的分身1只作为对象，将其横置。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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

            AutoAbility auto = registerAutoAbility(GameEventId.USE_SPELL, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!isState(CardStateFlag.DOWNED) && playerChoiceActivate() && down())
            {
                draw(1);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().anyLRIG()).get();
            down(target);
        }
    }
}
