package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_IronNaturalSource extends Card {

    public SIGNI_K3_IronNaturalSource()
    {
        setImageSets("WXDi-P09-082");

        setOriginalName("羅原　Fe");
        setAltNames("ラゲンテツ Ragen Tetsu Ferum");
        setDescription("jp",
                "@E %X %X：あなたのトラッシュに＜原子＞のシグニが７枚以上ある場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－12000する。"
        );

        setName("en", "Fe, Natural Element");
        setDescription("en",
                "@E %X %X: If there are seven or more <<Atom>> SIGNI in your trash, target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Iron, Natural Source");
        setDescription("en_fan",
                "@E %X %X: If there are 7 or more <<Atom>> SIGNI in your trash, target 1 of your opponent's SIGNI, and until end of turn, it gets --12000 power."
        );

		setName("zh_simplified", "罗原 Fe");
        setDescription("zh_simplified", 
                "@E %X %X:你的废弃区的<<原子>>精灵在7张以上的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-12000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ATOM).fromTrash().getValidTargetsCount() >= 7)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
