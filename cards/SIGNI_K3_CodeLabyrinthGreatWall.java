package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.modifiers.BasePowerModifier;

public final class SIGNI_K3_CodeLabyrinthGreatWall extends Card {

    public SIGNI_K3_CodeLabyrinthGreatWall()
    {
        setImageSets("WX24-P3-058");

        setOriginalName("コードラビリンス　バンリ");
        setAltNames("コードラビリンスバンリ Koodo Rabirinsu Banri");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、%K %Kを支払ってもよい。そうした場合、ターン終了時まで、このシグニは@>@C：このシグニの正面のシグニの基本パワーは０になる。@@を得る。\n" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Code Labyrinth Great Wall");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may pay %K %K. If you do, until end of turn, this SIGNI gains:" +
                "@>@C: The base power of the SIGNI in front of this SIGNI becomes 0.@@" +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "迷牢代号 万里长城");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，可以支付%K %K。这样做的场合，直到回合结束时为止，这只精灵得到\n" +
                "@>@C :这只精灵的正面的精灵的基本力量变为0。@@" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(payEner(Cost.color(CardColor.BLACK, 2)) && getCardIndex().isSIGNIOnField())
            {
                ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().opposite(), new BasePowerModifier(0));
                attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
            }
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
                gainPower(target, -15000, ChronoDuration.turnEnd());
            } else {
                draw(1);
            }
        }
    }
}
