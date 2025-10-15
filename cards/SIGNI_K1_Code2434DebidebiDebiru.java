package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K1_Code2434DebidebiDebiru extends Card {

    public SIGNI_K1_Code2434DebidebiDebiru()
    {
        setImageSets("WXDi-CP01-043");
        setLinkedImageSets("WXDi-CP01-030");

        setOriginalName("コード２４３４　でびでび・でびる");
        setAltNames("コードニジサンジデビデビデビル Koodo Nijisanji Debidebi Debiru");
        setDescription("jp",
                "@E %K：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。あなたの場に《コード２４３４　鷹宮リオン》がある場合、代わりにターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Debidebi Debiru, Code 2434");
        setDescription("en",
                "@E %K: Target SIGNI on your opponent's field gets --2000 power until end of turn. If there is \"Takamiya Rion, Code 2434\" on your field, it gets --5000 power until end of turn instead."
        );
        
        setName("en_fan", "Code 2434 Debidebi Debiru");
        setDescription("en_fan",
                "@E %K: Target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power. If there is \"Code 2434 Rion Takamiya\" on your field, instead, until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "2434代号 德比德比·德比鲁");
        setDescription("zh_simplified", 
                "@E %K:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。你的场上有《コード２４３４　鷹宮リオン》的场合，作为替代，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, new TargetFilter().own().SIGNI().withName("コード２４３４　鷹宮リオン").getValidTargetsCount() == 0 ? -2000 : -5000, ChronoDuration.turnEnd());
        }
    }
}
