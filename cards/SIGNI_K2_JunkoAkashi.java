package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K2_JunkoAkashi extends Card {

    public SIGNI_K2_JunkoAkashi()
    {
        setImageSets("WXDi-CP02-099");

        setOriginalName("赤司ジュンコ");
        setAltNames("アカシジュンコ Akashi Junko");
        setDescription("jp",
                "@A @[このシグニを場からトラッシュに置き、エナゾーンから＜ブルアカ＞のカード１枚をトラッシュに置く]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。" +
                "~{{C：このシグニのパワーは＋4000される。@@" +
                "~#：対戦相手のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );

        setName("en", "Akashi Junko");
        setDescription("en",
                "@A @[Put this SIGNI on your field into its owner's trash and put a <<Blue Archive>> card from your Ener Zone into your trash]@: Target SIGNI on your opponent's field gets --5000 power until end of turn.~{{C: This SIGNI gets +4000 power.@@" +
                "~#You may discard a card. If you do, target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Junko Akashi");
        setDescription("en_fan",
                "@A @[Put this SIGNI from the field into the trash, and put 1 <<Blue Archive>> card from your ener zone into the trash]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power." +
                "~{{C: This SIGNI gets +4000 power.@@" +
                "~#Target 1 of your opponent's SIGNI, and you may discard 1 card from your hand. If you do, until end of turn, it gets --12000 power."
        );

		setName("zh_simplified", "赤司纯子");
        setDescription("zh_simplified", 
                "@A 这只精灵从场上放置到废弃区，从能量区把<<ブルアカ>>牌1张放置到废弃区:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。\n" +
                "~{{C:这只精灵的力量+4000。@@" +
                "~#对战对手的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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

            registerActionAbility(new AbilityCostList(new TrashCost(), new TrashCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner())), this::onActionEff);

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -5000, ChronoDuration.turnEnd());
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
