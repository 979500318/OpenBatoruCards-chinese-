package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_B1_AyaFessoneSmallTrap extends Card {

    public SIGNI_B1_AyaFessoneSmallTrap()
    {
        setImageSets("WXDi-P14-058");

        setOriginalName("小罠　あや//フェゾーネ");
        setAltNames("ショウビンアヤフェゾーネ Shoubin Aya Fezoone");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場にレベル３の覚醒状態のシグニがある場合、カードを１枚引く。\n" +
                "@A $T1 #C #C：対戦相手のシグニ１体と、対戦相手のトラッシュからシグニ１枚を対象とし、それらのレベルが同じ場合、それらの場所を入れ替える。この方法で場に出たシグニの@E能力は発動しない。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );

        setName("en", "Aya//Fesonne, Small Trickster");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is a level three awakened SIGNI on your field, draw a card.\n@A $T1 #C #C: If target SIGNI on your opponent's field and target SIGNI in your opponent's trash are the same level, swap their positions. The @E abilities of SIGNI put onto the field this way do not activate." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Aya//Fessone, Small Trap");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there is a level 3 awakened SIGNI on your field, draw 1 card.\n" +
                "@A $T1 #C #C: Target 1 of your opponent's SIGNI, and 1 SIGNI from your opponent's trash, and if they are the same level, exchange their positions. The @E abilities of the SIGNI that entered the field this way don't activate." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );

		setName("zh_simplified", "小罠 亚弥//音乐节");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的场上有等级3的觉醒状态的精灵的场合，抽1张牌。\n" +
                "@A $T1 #C #C对战对手的精灵1只和，从对战对手的废弃区把精灵1张作为对象，这些的等级相同的场合，将这些的场所交换。这个方法出场的精灵的@E能力不能发动。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            ActionAbility act = registerActionAbility(new CoinCost(2), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().withState(CardStateFlag.AWAKENED).withLevel(3).getValidTargetsCount() > 0)
            {
                draw(1);
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MOVE).OP().SIGNI()).get();
            CardIndex targetFromTrash = playerTargetCard(new TargetFilter(TargetHint.MOVE).OP().SIGNI().fromTrash().playableAs(target)).get();
            
            if(target != null && targetFromTrash != null &&
               target.getIndexedInstance().getLevelByRef() == targetFromTrash.getIndexedInstance().getLevelByRef())
            {
                trash(target);
                putOnField(targetFromTrash, target.getPreTransientLocation(), Enter.DONT_ACTIVATE);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
