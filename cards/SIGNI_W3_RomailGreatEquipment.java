package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W3_RomailGreatEquipment extends Card {

    public SIGNI_W3_RomailGreatEquipment()
    {
        setImageSets("WX24-D1-20");

        setOriginalName("大装　ローメイル");
        setAltNames("タイソウローメイル Daisou Roomairu");
        setDescription("jp",
                "@C $TP：あなたのシグニのパワーを＋3000する。&E５枚以上@0代わりにあなたのシグニのパワーを＋5000する。" +
                "~#どちらか１つを選ぶ。\n$$1対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。\n$$2カードを１枚引く。"
        );

        setName("en", "Romail, Great Equipment");
        setDescription("en",
                "@C $TP: All of your SIGNI get +3000 power. &E5 or more@0 Instead, all of your SIGNI get +5000 power." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and return it to their hand.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "大装 皇家铠");
        setDescription("zh_simplified", 
                "@C $TP :你的精灵的力量+3000。\n" +
                "&E5张以上@0作为替代，你的精灵的力量+5000。\n" +
                "（你的分身废弃区有5张以上的必杀时，则&E5张以上@0后的文字变为有效）" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ConstantAbility cont;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            cont = registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI(), new PowerModifier(this::onConstEffSharedModGetValue));
            cont.setRecollect(5);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private double onConstEffSharedModGetValue(CardIndex cardIndex)
        {
            return !cont.isRecollectFulfilled() ? 3000 : 5000;
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
                addToHand(target);
            } else {
                draw(1);
            }
        }
    }
}
